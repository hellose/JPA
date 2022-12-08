package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//기본키 맵핑 - @Id, @GeneratedValue

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

}
