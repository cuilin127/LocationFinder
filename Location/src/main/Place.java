package main;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class Place {
	String id;
	String name;
	String address;
	String phoneNumber;
	String website;
	public String toString() {
		return "ID: "+this.id+
				"\nName: " + this.name +
				"\nAddress: "+ this.address +
				"\nPhone Number: "+ this.phoneNumber +
				"\nWebsite: "+ this.website;
		
	}
}
