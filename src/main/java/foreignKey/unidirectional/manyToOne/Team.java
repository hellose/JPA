package foreignKey.unidirectional.manyToOne;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Getter
@Setter
@NoArgsConstructor
class Team {

	@Id
	@Column(name="team_id")
	private Long teamId;
	
	@Column(name="team_name")
	private String teamName;
}