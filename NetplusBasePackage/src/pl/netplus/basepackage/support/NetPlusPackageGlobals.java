package pl.netplus.basepackage.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import pl.netplus.basepackage.entities.ContentObject;
import pl.netplus.basepackage.entities.Favorite;

public class NetPlusPackageGlobals {
	public static final int ITEMS_ALL = -2;
	public static final int ITEMS_FAVORITE = -1;
	public static final int ITEMS_SEARCH = -3;
	public static final int ITEMS_NEET_UPDATE = -4;
	public static final int ITEMS_LATEST = -5;
	public static final int ITEMS_RANDOM = -6;
	public static final int ITEMS_THE_BEST = -7;

	public static final int SORT_BY_DATE = 1;
	public static final int SORT_BY_RATE = 2;
	private static final int SORT_NO_SORT = 3;

	private static volatile NetPlusPackageGlobals instance = null;

	private ArrayList<Integer> favorites;
	private HashMap<Integer, ArrayList<ContentObject>> objectsDictionary;
	private boolean hideEmptyCategories;

	public static NetPlusPackageGlobals getInstance() {
		if (instance == null) {
			synchronized (NetPlusPackageGlobals.class) {
				if (instance == null) {
					instance = new NetPlusPackageGlobals();
				}
			}
		}
		return instance;
	}

	private NetPlusPackageGlobals() {
	}

	public void setHideEmptyCategories(boolean hideEmptyCategories) {
		this.hideEmptyCategories = hideEmptyCategories;
	}

	public void setObjectsDictionary(int categoryId,
			ArrayList<ContentObject> contentObjects) {
		if (this.objectsDictionary == null)
			this.objectsDictionary = new HashMap<Integer, ArrayList<ContentObject>>();

		if (this.objectsDictionary.containsKey(categoryId)) {
			this.objectsDictionary.remove(categoryId);
		}
		this.objectsDictionary.put(categoryId, contentObjects);
	}

	public ArrayList<ContentObject> getCategoriesContentObjects(int categoryId,
			int sortOption) {

		if (categoryId == ITEMS_LATEST || categoryId == ITEMS_RANDOM
				|| categoryId == ITEMS_THE_BEST) {
			if (categoryId == ITEMS_LATEST)
				sortOption = SORT_BY_DATE;
			if (categoryId == ITEMS_THE_BEST)
				sortOption = SORT_BY_RATE;
			if (categoryId == ITEMS_RANDOM)
				sortOption = SORT_NO_SORT;
			categoryId = ITEMS_ALL;
		}

		if (this.objectsDictionary != null) {
			if (this.objectsDictionary.containsKey(categoryId)) {
				ArrayList<ContentObject> items = this.objectsDictionary
						.get(categoryId);

				// Jeœli znajdujê siê na liœcie ulubionych, to go oznacz
				if (favorites != null) {
					for (ContentObject contentObject : items) {
						if (favorites.contains(contentObject.getId()))
							contentObject.setFavorites(true);
					}
				}

				if (sortOption == SORT_NO_SORT) {

				} else if (sortOption == SORT_BY_DATE) {
					Collections.sort(items, new Comparator<ContentObject>() {
						@Override
						public int compare(ContentObject c1, ContentObject c2) {
							return (c1.getUploadDate() > c2.getUploadDate() ? -1
									: (c1.getUploadDate() == c2.getUploadDate() ? 0
											: 1));
						}
					});
				} else if (sortOption == SORT_BY_RATE) {
					Collections.sort(items, new Comparator<ContentObject>() {
						@Override
						public int compare(ContentObject c1, ContentObject c2) {
							try {
								if (c1.getRating() < c2.getRating())
									return 1;
								else if (c1.getRating() > c2.getRating())
									return -1;
								else
									return 0;
							} catch (Exception e) {
								return 0;
							}

						}
					});
				}
				return items;
			}
		}
		return null;
	}

	public void setFavoritesId(ArrayList<Favorite> favorites) {
		this.favorites = new ArrayList<Integer>();
		for (Favorite favorite : favorites) {
			this.favorites.add(favorite.getObjectId());
		}
	}

	public ArrayList<Integer> getFavoritesId() {
		return this.favorites;
	}

	public int getFavoritesCount() {
		if (this.favorites != null)
			return this.favorites.size();
		return 0;
	}

	public void removedFromFavorites(int objectId) {
		if (this.favorites != null)
			this.favorites.remove(new Integer(objectId));

	}

	public void addToFavorites(int objectId) {
		if (this.favorites == null)
			this.favorites = new ArrayList<Integer>();
		if (this.favorites != null)
			this.favorites.add(objectId);

	}
}
