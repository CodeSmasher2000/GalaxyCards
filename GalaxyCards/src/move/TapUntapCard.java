package move;

import enumMessage.Lanes;

public class TapUntapCard {
	private int id;
	private Lanes ENUM;

	public TapUntapCard(int id, Lanes ENUM) {
		this.setId(id);
		this.setENUM(ENUM);
	}

	public Lanes getENUM() {
		return ENUM;
	}

	public void setENUM(Lanes eNUM) {
		ENUM = eNUM;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
