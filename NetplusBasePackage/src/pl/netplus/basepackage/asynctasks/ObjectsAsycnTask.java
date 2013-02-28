package pl.netplus.basepackage.asynctasks;

import java.util.ArrayList;

import pl.netplus.basepackage.database.DataBaseManager;
import pl.netplus.basepackage.entities.ModelBase;
import pl.netplus.basepackage.enums.ERepositoryException;
import pl.netplus.basepackage.enums.ERepositoryManagerMethods;
import pl.netplus.basepackage.enums.ExceptionErrorCodes;
import pl.netplus.basepackage.exceptions.CommunicationException;
import pl.netplus.basepackage.exceptions.RepositoryException;
import pl.netplus.basepackage.httpproviders.IHttpRequestToAsyncTaskCommunication;
import pl.netplus.basepackage.interfaces.IBaseRepository;
import pl.netplus.basepackage.interfaces.IReadRepository;
import pl.netplus.basepackage.repositories.ContentObjectRepository;
import android.os.AsyncTask;
import android.os.Bundle;

public class ObjectsAsycnTask extends AsyncTask<Void, Void, Void> implements
		IHttpRequestToAsyncTaskCommunication {

	private AsyncTaskResult response;
	private IReadRepository listener;
	private ERepositoryManagerMethods method;
	private IBaseRepository repository;

	private int elementCount = 2;
	private int downloadElementCount = 0;

	private ModelBase item;
	private int categoryId;
	private String value;
	private Bundle bundle;
	private boolean showProgress = false;

	public ObjectsAsycnTask(IReadRepository listener,
			ERepositoryManagerMethods method, IBaseRepository repository,
			int categoryId, String value) {
		this.listener = listener;
		this.method = method;
		this.repository = repository;
		this.categoryId = categoryId;
		this.value = value;
	}

	public ObjectsAsycnTask(IReadRepository listener,
			ERepositoryManagerMethods method, IBaseRepository<?> repository,
			ModelBase item, Bundle bundle, Boolean showProgress) {
		this.listener = listener;
		this.method = method;
		this.repository = repository;
		this.bundle = bundle;
		this.showProgress = showProgress;
		this.item = item;
	}

	@Override
	protected Void doInBackground(Void... params) {
		response = new AsyncTaskResult();
		try {
			if (method != ERepositoryManagerMethods.UpdateData
					&& repository == null)
				throw new CommunicationException("",
						ExceptionErrorCodes.NoRepository);
			switch (method) {

			case UpdateData: {

				long results = -1;
				DataBaseManager dbm = DataBaseManager.getInstance();
				dbm.checkIsOpen();
				dbm.getDataBase().beginTransaction();
				try {
					downloadElementCount = 0;
					String addressObjects = bundle.getString("ObjectsLink");
					String toDeleteObjects = bundle
							.getString("ObjectsToDeleteLink");

					ContentObjectRepository objrep = new ContentObjectRepository();

					downloadElementCount++;

					results = objrep.getFromServer(dbm, addressObjects, null);
					if (results < 0)
						throw new Exception();
					onObjectsProgressUpdate(100);
					downloadElementCount++;

					ArrayList test = objrep.readAll(bundle);

					long tmpResult = objrep.getObjectsToDeleteFromServer(dbm,
							toDeleteObjects, this);
					if (tmpResult < 0)
						throw new Exception();
					onObjectsProgressUpdate(100);
					downloadElementCount++;

					dbm.getDataBase().setTransactionSuccessful();
				} catch (Exception e) {
					throw new CommunicationException("",
							ExceptionErrorCodes.UpdateError);
				} finally {
					dbm.getDataBase().endTransaction();
				}
				dbm.close();

				if (results == -1)
					throw new CommunicationException("",
							ExceptionErrorCodes.UpdateError);
				response.bundle = results;
			}
				break;
			case InsertOrUpdate:
				response.bundle = repository.insertOrUpdate(item, null);
				break;
			case Read:
				break;
			case Search:
				if (repository instanceof ContentObjectRepository) {
					response.bundle = ((ContentObjectRepository) repository)
							.searchObjects(categoryId, value);
				} else
					throw new CommunicationException("",
							ExceptionErrorCodes.InvalidRepository);
				break;
			case ReadById:
				response.bundle = repository.readById(item.getId(), bundle);
				break;
			case ReadAll:
				response.bundle = repository.readAll(bundle);
				break;
			case ReadFavorites:
				if (repository instanceof ContentObjectRepository) {
					response.bundle = ((ContentObjectRepository) repository)
							.readFavorites();
				} else
					throw new CommunicationException("",
							ExceptionErrorCodes.InvalidRepository);
			default:
				break;
			}

		} catch (Exception ex) {
			response.exception = new RepositoryException(
					ERepositoryException.AsyncTaskException, ex);
		}

		return null;
	}

	@Override
	public void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (listener != null) {
			listener.onTaskEnd();
			if (response.exception == null)
				listener.onTaskResponse(response);
			else
				listener.onTaskInvalidResponse(response.exception);
		}
	}

	@Override
	public void onPreExecute() {
		super.onPreExecute();
		if (listener != null) {
			listener.onTaskStart("", showProgress);
		}
	}

	public void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
		if (listener != null) {
			listener.onTaskProgressUpdate(1);
		}
	}

	public class AsyncTaskResult {
		public Object bundle;
		public RepositoryException exception;
	}

	@Override
	public void onObjectsProgressUpdate(int progressPercent) {
		if (listener != null) {
			// if (progressPercent > 0) {
			int actualProgress = 100 / elementCount * downloadElementCount;
			actualProgress = actualProgress + progressPercent / elementCount;
			listener.onTaskProgressUpdate(actualProgress);
			// }
		}

	}

	@Override
	public boolean checkIsTaskCancled() {
		// TODO Auto-generated method stub
		return false;
	}

}
