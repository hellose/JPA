package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
// member_seq_generator
@TableGenerator(name = "member_seq_generator", table = "my_sequence_table", pkColumnValue = "member_seq", allocationSize = 1)
public class Member {

	@Id
	// member_seq_generator
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
	private Long id;

}
