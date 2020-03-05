package dbz;

import dbz.domain.BoardName;
import dbz.domain.Emblem;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

import static dbz.domain.Emblem.*;

public class LinkBonusFactory {

    @Getter
    private Set<LinkBonus> allLinkBonuses;

    public LinkBonusFactory() {
        allLinkBonuses = Set.of(
                new LinkBonus(Set.of(GOKU, GOHAN), 2),
                new LinkBonus(Set.of(GOKU, KRILLIN, YAMCHA), 3),
                new LinkBonus(Set.of(GOKU, VEGETA, PICCOLO, TIEN), 4),
                new LinkBonus(Set.of(GOKU, MR_SATAN), 8),

                new LinkBonus(Set.of(GOHAN, PICCOLO), 2),
                new LinkBonus(Set.of(GOHAN, VIDEL), 2),
                new LinkBonus(Set.of(GOHAN, GOTEN, CHICHI), 3),

                new LinkBonus(Set.of(CHICHI, OX_KING), 2),
                new LinkBonus(Set.of(CHICHI, ANDROID_18), 2),

                new LinkBonus(Set.of(MASTER_ROSHI, TURTLE), 2),
                new LinkBonus(Set.of(MASTER_ROSHI, TURTLE, LAUNCH), 3),
                new LinkBonus(Set.of(MASTER_ROSHI, MASTER_SHEN), 2),
                new LinkBonus(Set.of(MASTER_ROSHI, BABA), 2),

                new LinkBonus(Set.of(NAM, W_T_ANNOUNCER), 2),

                new LinkBonus(Set.of(ANDROID_8, SUNO), 2),
                new LinkBonus(Set.of(ANDROID_8, ANDROID_16, ANDROID_17), 3),

                new LinkBonus(Set.of(KING_YEMMA, KAMI), 2),
                new LinkBonus(Set.of(KING_YEMMA, DABURA), 2),
                new LinkBonus(Set.of(KING_YEMMA, KING_COLD, OX_KING), 3),

                new LinkBonus(Set.of(KAMI, DENDE), 2),
                new LinkBonus(Set.of(KAMI, MR_POPO), 2),
                new LinkBonus(Set.of(KAMI, PICCOLO, NAIL), 3),

                new LinkBonus(Set.of(YAJIROBE, TRUNKS), 2),
                new LinkBonus(Set.of(YAJIROBE, KORIN), 2),

                new LinkBonus(Set.of(TIEN, YAMCHA), 2),
                new LinkBonus(Set.of(TIEN, LAUNCH), 2),
                new LinkBonus(Set.of(TIEN, CHIAOTZU, MASTER_SHEN), 3),

                new LinkBonus(Set.of(CHIAOTZU, ANDROID_19), 2),
                new LinkBonus(Set.of(CHIAOTZU, GULDO, BABIDI), 3),
                new LinkBonus(Set.of(CHIAOTZU, CELL, SAIBAMAN, VEGETA), 4),

                new LinkBonus(Set.of(KRILLIN, FRIEZA), 2),
                new LinkBonus(Set.of(KRILLIN, ANDROID_18, MARRON), 3),

                new LinkBonus(Set.of(YAMCHA, PUAR), 2),
                new LinkBonus(Set.of(YAMCHA, SAIBAMAN), 2),

                new LinkBonus(Set.of(PICCOLO, DENDE, NAIL, GURU), 4),

                new LinkBonus(Set.of(KING_KAI, BUBBLES), 2),
                new LinkBonus(Set.of(KING_KAI, KIBITO, SUPREME_KAI, ELDER_KAI), 4),

                new LinkBonus(Set.of(PUAR, OOLONG), 2),

                new LinkBonus(Set.of(MR_POPO, BURTER), 2),

                new LinkBonus(Set.of(PILAF, BULMA), 2),
                new LinkBonus(Set.of(PILAF, SHU, MAI), 3),

                new LinkBonus(Set.of(SHU, TURTLE), 2),
                new LinkBonus(Set.of(MAI, TRUNKS), 2),

                new LinkBonus(Set.of(LAUNCH, MAJIN_BUU), 2),

                new LinkBonus(Set.of(SAIBAMAN, NAPPA), 2),
                new LinkBonus(Set.of(SAIBAMAN, CELL_JR), 2),

                new LinkBonus(Set.of(MERCENARY_TAO, MASTER_SHEN), 2),
                new LinkBonus(Set.of(MERCENARY_TAO, BORA), 2),
                new LinkBonus(Set.of(MERCENARY_TAO, FRIEZA), 2),
                new LinkBonus(Set.of(MERCENARY_TAO, ARALE), 2),

                new LinkBonus(Set.of(BULMA, DOCTOR_BRIEFS), 2),
                new LinkBonus(Set.of(BULMA, TRUNKS, TRUNKS_KID, VEGETA), 4),

                new LinkBonus(Set.of(VEGETA, RADITZ, NAPPA), 3),

                new LinkBonus(Set.of(GURU, KORIN, ELDER_KAI), 3),

                new LinkBonus(Set.of(OOLONG, SHENRON), 2),

                new LinkBonus(Set.of(DOCTOR_BRIEFS, MR_SATAN, OX_KING), 12),

                new LinkBonus(Set.of(KORIN, PUPPY), 2),
                new LinkBonus(Set.of(KORIN, BORA, UPA), 3),

                new LinkBonus(Set.of(BABA, W_T_ANNOUNCER), 2),

                new LinkBonus(Set.of(GULDO, RECOOME, BURTER, JEICE, CAPTAIN_GINYU), 5),
                new LinkBonus(Set.of(GULDO, RECOOME, BURTER, JEICE, CAPTAIN_GINYU, BONYU), 1),

                new LinkBonus(Set.of(RECOOME, ANDROID_16), 2),

                new LinkBonus(Set.of(JEICE, CAPTAIN_GINYU), 2),

                new LinkBonus(Set.of(DENDE, PORUNGA), 2),

                new LinkBonus(Set.of(SHENRON, PORUNGA), 2),

                new LinkBonus(Set.of(CUI, APPULE), 2),
                new LinkBonus(Set.of(CUI, APPULE, ZARBON, DODORIA), 4),

                new LinkBonus(Set.of(CELL_JR, CELL), 2),

                new LinkBonus(Set.of(RADITZ, DODORIA, KING_COLD), 3),

                new LinkBonus(Set.of(ZARBON, YAKON), 2),

                new LinkBonus(Set.of(ANDROID_18, ARALE), 2),
                new LinkBonus(Set.of(ANDROID_18, ANDROID_17, CELL), 3),

                new LinkBonus(Set.of(VIDEL, MR_SATAN), 8),

                new LinkBonus(Set.of(SUPREME_KAI, KIBITO), 2),

                new LinkBonus(Set.of(GOTEN, TRUNKS_KID), 2),

                new LinkBonus(Set.of(MR_SATAN, MAJIN_BUU, PUPPY), 12),

                new LinkBonus(Set.of(DABURA, MAJIN_BUU, BABIDI), 3),

                new LinkBonus(Set.of(CELL, ANDROID_20), 2),

                new LinkBonus(Set.of(MAJIN_BUU, GATCHAN), 2),

                new LinkBonus(Set.of(BORA, UPA), 2),

                new LinkBonus(Set.of(FRIEZA, KING_COLD), 2),
                new LinkBonus(Set.of(FRIEZA, BABIDI), 2),

                new LinkBonus(Set.of(ARALE, SENBEI, GATCHAN), 20),

                new LinkBonus(Set.of(SENBEI, ANDROID_20), 2),

                new LinkBonus(Set.of(CAPTAIN_GINYU, BONYU), 2),

                new LinkBonus(Set.of(ANDROID_20, ANDROID_19), 2),
                new LinkBonus(Set.of(ANDROID_20, ANDROID_19, YAMU, SPOPOVICH), 4),

                new LinkBonus(Set.of(YAMU, SPOPOVICH), 2),

                new LinkBonus(Set.of(PUI_PUI, YAKON), 2)
        );
    }

    public Set<LinkBonus> getLinkBonusesByEmblem(Emblem emblem) {
        return allLinkBonuses.stream()
                .filter(link -> link.getLinkMembers().contains(emblem))
                .collect(Collectors.toSet());
    }

    public boolean doEmblemsShareLink(Emblem firstEmblem, Emblem secondEmblem) {
        return getLinkBonusesByEmblem(firstEmblem).stream()
                .anyMatch(link -> link.getLinkMembers().contains(secondEmblem));
    }

    public Set<LinkBonus> linksContainingAllEmblems(Set<Emblem> emblems) {
        return allLinkBonuses.stream()
                .filter(link -> CollectionUtils.containsAll(emblems, link.getLinkMembers()))
                .collect(Collectors.toSet());
    }

    public Set<LinkBonus> linksContainingEmblems(Set<Emblem> emblems) {
        return allLinkBonuses.stream()
                .filter(link -> CollectionUtils.containsAll(link.getLinkMembers(), emblems))
                .collect(Collectors.toSet());
    }

    public Set<LinkBonus> linksContainingAnyEmblems(Set<Emblem> emblems) {
        return allLinkBonuses.stream()
                .filter(link -> CollectionUtils.containsAny(link.getLinkMembers(), emblems))
                .collect(Collectors.toSet());
    }

    public Set<LinkBonus> linksContainingAnyEmblems(Set<Emblem> emblems, BoardName currentBoard) {
        return linksContainingAnyEmblems(emblems).stream()
                .filter(link -> link.getLinkMembers().stream()
                        .allMatch(e -> currentBoard == null || e.getHomeBoard() == currentBoard || e.getHomeBoard() == null))
                .collect(Collectors.toSet());
    }
}
