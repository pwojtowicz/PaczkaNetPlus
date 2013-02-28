package pl.netplus.basepackage.support;

public class StringHelper {

	public static String removePolishChars(String text) {

		text = text.replace('Í', 'e');
		text = text.replace('Û', 'o');
		text = text.replace('π', 'a');
		text = text.replace('ú', 's');
		text = text.replace('≥', 'l');
		text = text.replace('ø', 'z');
		text = text.replace('ü', 'z');
		text = text.replace('Ê', 'c');
		text = text.replace('Ò', 'n');

		text = text.replace(' ', 'E');
		text = text.replace('”', 'O');
		text = text.replace('•', 'A');
		text = text.replace('å', 'S');
		text = text.replace('£', 'L');
		text = text.replace('Ø', 'Z');
		text = text.replace('è', 'Z');
		text = text.replace('∆', 'C');
		text = text.replace('—', 'N');
		return text;
	}

	public static String daysCountToString(long days) {

		if (days <= 0)
			return " dzisiaj";
		if (days == 1)
			return " wczoraj";

		if (days > 1 && days < 30)
			return String.format(" %d dni temu", days);

		if (days >= 30 && days < 365) {
			int months = (int) (days / 30);
			String name = "miesiπc";
			if (months == 1)
				return String.format(" %s temu", name);
			if (months > 1 && months < 5)
				name = "miesiπce";
			if (months >= 5)
				name = "miesiÍcy";
			return String.format(" %d %s temu", months, name);
		}

		return " ponad rok temu";

	}
}
