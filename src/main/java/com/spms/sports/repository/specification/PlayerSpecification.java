package com.spms.sports.repository.specification;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import com.spms.sports.entity.Player;
import com.spms.sports.entity.Sport;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class PlayerSpecification 
{

    public static Specification<Player> hasSports(List<String> sportNames) 
    {
        return (root, query, criteriaBuilder) -> {
            if (sportNames == null || sportNames.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true = no filtering
            }

            Join<Player, Sport> sportsJoin = root.join("sports", JoinType.INNER);
            return sportsJoin.get("name").in(sportNames);
        };
    }    
}
