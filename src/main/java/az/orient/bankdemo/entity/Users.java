package az.orient.bankdemo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50, unique = true)
	private String email;
	@Column(nullable = false, length = 64)
	private String password;
	private String role;
	@Column(name = "active")
	@ColumnDefault(value = "1")
	private Integer active;

	public Users(String email, String password) {
		this.email = email;
		this.password = password;
	}


}
