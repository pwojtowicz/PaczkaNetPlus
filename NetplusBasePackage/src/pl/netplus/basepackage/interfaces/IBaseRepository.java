package pl.netplus.basepackage.interfaces;

import java.util.ArrayList;

import pl.netplus.basepackage.database.DataBaseManager;
import pl.netplus.basepackage.httpproviders.IHttpRequestToAsyncTaskCommunication;
import android.os.Bundle;

public interface IBaseRepository<T> {

	public abstract boolean insertOrUpdate(T item, DataBaseManager dbManager);

	public abstract T read(int id);

	public abstract T read(int id, DataBaseManager dbManager);

	public abstract ArrayList<T> readById(int value, Bundle bundle);

	public abstract ArrayList<T> readAll(Bundle bundle);

	public abstract boolean delete(T item);

	public long getFromServer(DataBaseManager dbManager, String urlAddress,
			IHttpRequestToAsyncTaskCommunication listener);

}
