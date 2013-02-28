package pl.netplus.basepackage.managers;

import pl.netplus.basepackage.asynctasks.ObjectsAsycnTask;
import pl.netplus.basepackage.entities.ModelBase;
import pl.netplus.basepackage.enums.ERepositoryManagerMethods;
import pl.netplus.basepackage.enums.ERepositoryTypes;
import pl.netplus.basepackage.interfaces.IBaseRepository;
import pl.netplus.basepackage.interfaces.IReadRepository;
import pl.netplus.basepackage.repositories.ContentObjectRepository;
import pl.netplus.basepackage.repositories.FavoritesRepository;
import android.os.Bundle;

public class ObjectManager {

	private IBaseRepository<?> getRepository(ERepositoryTypes type) {
		IBaseRepository<?> repository = null;
		switch (type) {
		case ContentObject:
			repository = new ContentObjectRepository();
			break;
		case Favorite:
			repository = new FavoritesRepository();
			break;
		default:
			break;
		}
		return repository;
	}

	public void updateData(Bundle b, IReadRepository listener) {
		startTask(listener, ERepositoryManagerMethods.UpdateData, b);
	}

	private void startTask(IReadRepository listener,
			ERepositoryManagerMethods method, Bundle b) {
		ObjectsAsycnTask task;
		// task = new ObjectsAsycnTask(listener, method, b);
		task = new ObjectsAsycnTask(listener, method, null, null, b, true);
		task.execute((Void) null);
	}

	private void startTask(IReadRepository listener,
			IBaseRepository<?> repository, ERepositoryManagerMethods method,
			ModelBase item, Bundle bundle) {
		ObjectsAsycnTask task;
		task = new ObjectsAsycnTask(listener, method, repository, item, bundle,
				false);
		task.execute((Void) null);
	}

	public void readObjectsWithoutSendItem(IReadRepository listener,
			ERepositoryTypes type, ERepositoryManagerMethods method,
			Bundle bundle) {
		startTask(listener, getRepository(type), method, null, bundle);
	}

	public void readObjectsWithSendItem(IReadRepository listener,
			ERepositoryTypes type, ERepositoryManagerMethods method,
			ModelBase item, Bundle bundle) {
		startTask(listener, getRepository(type), method, item, bundle);
	}

	public void searchContentObjects(IReadRepository listener, int categoryId,
			String value) {
		ObjectsAsycnTask task;
		task = new ObjectsAsycnTask(listener, ERepositoryManagerMethods.Search,
				getRepository(ERepositoryTypes.ContentObject), categoryId,
				value);
		task.execute((Void) null);
	}

	public void insertOrUpdate(IReadRepository listener, ERepositoryTypes type,
			ModelBase item) {

		startTask(listener, getRepository(type),
				ERepositoryManagerMethods.InsertOrUpdate, item, null);
	}

}
