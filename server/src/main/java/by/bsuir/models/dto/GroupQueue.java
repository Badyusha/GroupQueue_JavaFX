package by.bsuir.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class GroupQueue implements Serializable {
	private int numberInQueue;
	private String lastName;
	private String firstName;
	private String username;
	private byte[] passingLabs;
}
