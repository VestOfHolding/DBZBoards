package dbz.genetic;

import dbz.domain.BoardName;
import dbz.domain.Emblem;

public record Gene(Emblem emblem, BoardName board, int vertexID) { }
