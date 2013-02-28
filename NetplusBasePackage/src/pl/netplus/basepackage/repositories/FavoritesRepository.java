package pl.netplus.basepackage.repositories;

import java.util.ArrayList;

import pl.netplus.basepackage.database.DataBaseHelper;
import pl.netplus.basepackage.database.DataBaseManager;
import pl.netplus.basepackage.entities.Favorite;
import pl.netplus.basepackage.httpproviders.IHttpRequestToAsyncTaskCommunication;
import pl.netplus.basepackage.interfaces.IBaseRepository;
import pl.netplus.basepackage.support.NetPlusPackageGlobals;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

public class FavoritesRepository implements IBaseRepository<Favorite> {

	DataBaseManager dbm = DataBaseManager.getInstance();

	private final String INSERT_TO_FAVORITES = "INSERT INTO "
			+ DataBaseHelper.TABLE_FAVORITES + "(ObjectId)  Values(?)";

	@Override
	public Favorite read(int id) {
		return null;
	}

	@Override
	public ArrayList<Favorite> readAll(Bundle bundle) {
		dbm.checkIsOpen();
		ArrayList<Favorite> list = new ArrayList<Favorite>();
		Cursor cursor = dbm.getDataBase().query(DataBaseHelper.TABLE_FAVORITES,
				new String[] { "ID,ObjectId" }, null, null, null, null, "ID");
		if (cursor.moveToFirst()) {
			do {
				Favorite item = new Favorite();
				item.setId(cursor.getInt(0));
				item.setObjectId(cursor.getInt(1));

				list.add(item);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		dbm.close();

		NetPlusPackageGlobals.getInstance().setFavoritesId(list);
		return list;
	}

	public ArrayList<Favorite> readAllForDataBaseUpdate(SQLiteDatabase db) {

		ArrayList<Favorite> list = new ArrayList<Favorite>();
		Cursor cursor = db.query(DataBaseHelper.TABLE_FAVORITES,
				new String[] { "ID,ObjectId" }, null, null, null, null, "ID");
		if (cursor.moveToFirst()) {
			do {
				Favorite item = new Favorite();
				item.setId(cursor.getInt(0));
				item.setObjectId(cursor.getInt(1));

				list.add(item);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return list;
	}

	public boolean insertOrUpdateAfterDataBaseUpdate(Favorite item,
			SQLiteDatabase db) {
		if (item != null) {

			SQLiteStatement insertStmt = db
					.compileStatement(INSERT_TO_FAVORITES);
			insertStmt.bindLong(1, item.getObjectId());
			long result = insertStmt.executeInsert();

			return true;
		}

		return false;

	}

	@Override
	public boolean insertOrUpdate(Favorite item, DataBaseManager dbManager) {
		if (item.isFavorite()) {
			dbm.checkIsOpen();
			SQLiteStatement insertStmt = dbm.getDataBase().compileStatement(
					INSERT_TO_FAVORITES);
			insertStmt.bindLong(1, item.getObjectId());
			long result = insertStmt.executeInsert();
			dbm.close();

			if (result > 0) {
				NetPlusPackageGlobals.getInstance().addToFavorites(
						item.getObjectId());
				return true;
			}
			return false;
		} else {
			return delete(item);
		}

	}

	@Override
	public long getFromServer(DataBaseManager dbManager, String urlAddress,
			IHttpRequestToAsyncTaskCommunication listener) {
		return -1;
	}

	@Override
	public boolean delete(Favorite item) {
		dbm.checkIsOpen();

		int result = dbm.getDataBase().delete(DataBaseHelper.TABLE_FAVORITES,
				"ObjectId = ?",
				new String[] { String.valueOf(item.getObjectId()) });
		dbm.close();

		if (result > 0) {
			NetPlusPackageGlobals.getInstance().removedFromFavorites(
					item.getObjectId());
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Favorite> readById(int value, Bundle bundle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Favorite read(int id, DataBaseManager dbManager) {
		// TODO Auto-generated method stub
		return null;
	}

}
