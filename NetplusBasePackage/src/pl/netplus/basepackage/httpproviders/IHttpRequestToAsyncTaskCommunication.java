package pl.netplus.basepackage.httpproviders;

public interface IHttpRequestToAsyncTaskCommunication {

	void onObjectsProgressUpdate(int progressPercent);

	boolean checkIsTaskCancled();

}
