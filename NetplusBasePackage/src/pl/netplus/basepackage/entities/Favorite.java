package pl.netplus.basepackage.entities;

public class Favorite extends ModelBase {

	private int objectId;

	private boolean isFavorite;

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

}
