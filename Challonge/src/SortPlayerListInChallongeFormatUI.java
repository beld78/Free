import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class SortPlayerListInChallongeFormatUI {
	boolean exitUI = false;

	ConsoleHelper helper;
	List<String[]> groups = new LinkedList<>();
	List<String> participants = new LinkedList<>();
	int amountOfGroups;
	int amountOfPersonsPerGroup;
	int participantPlace = 0;
	String path;

	public SortPlayerListInChallongeFormatUI() {
		helper = ConsoleHelper.build();
	}

	public void startUI() {
		int inputSwitch;
		while (exitUI == false) {
			try {
				inputSwitch = helper.askInteger("Enter 0 to exit.\nEnter 1 to enter formatting menu.");
				switch (inputSwitch) {
				case 0:
					System.out.println("exit program");
					exitUI = true;
					break;
				case 1:
					System.out.println("entering format UI");
					startFormatUI();

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void startFormatUI() {
		createGroups();
		createParticipants();
		writeParticipantsInGroups();
		try {
			path = helper.askNonEmptyString("Enter path to save file with participants");
			Path textPath = Paths.get(path);
			try (BufferedWriter bw = Files.newBufferedWriter(textPath)) {
				for (int i = 0; i < groups.size(); i++) {
					for (int z = 0; z < groups.get(i).length; z++) {
						bw.write(groups.get(i)[z]);
						bw.newLine();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeParticipantsInGroups() {
		for (int i = 0; i < amountOfGroups; i++) {
			for (int z = 0; z < amountOfPersonsPerGroup; z++) {
				groups.get(z)[i] = participants.get(participantPlace);
				participantPlace++;
			}
		}
	}

	private void createParticipants() {
		try {
			path = helper.askNonEmptyString("Enter path for .txt file with participants");
			Path textPath = Paths.get(path);
			try (BufferedReader br = Files.newBufferedReader(textPath)) {
				String line = br.readLine();
				while (line != null) {

					participants.add(line);
					line = br.readLine();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createGroups() {
		try {
			amountOfGroups = helper.askInteger("Enter amount of groups:");
			amountOfPersonsPerGroup = helper.askInteger("Enter amount of persons per group:");
			for (int i = 0; i < amountOfGroups; i++) {
				String[] temp = new String[amountOfPersonsPerGroup];
				groups.add(temp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
