package by.bsuir.models.dto;

import by.bsuir.enums.entityAttributes.RequestType;
import by.bsuir.enums.entityAttributes.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Request implements Serializable {
	private long studentId;
	private String lastName;
	private String firstName;
	private String username;
	private RoleType roleType;
	private int groupNumber;
	private RequestType requestType;
}
