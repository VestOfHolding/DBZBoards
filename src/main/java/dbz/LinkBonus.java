package dbz;

import dbz.domain.Emblem;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class LinkBonus {
    private Set<Emblem> linkMembers;

    private int bonus;
}
