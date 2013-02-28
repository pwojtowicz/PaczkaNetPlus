package pl.netplus.basepackage.interfaces;

import pl.netplus.basepackage.asynctasks.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.basepackage.exceptions.RepositoryException;

public interface IReadRepository {

	void onTaskEnd();

	void onTaskStart(String message, boolean showProgress);

	void onTaskResponse(AsyncTaskResult response);

	void onTaskInvalidResponse(RepositoryException exception);

	void onTaskProgressUpdate(int actualProgress);

}
