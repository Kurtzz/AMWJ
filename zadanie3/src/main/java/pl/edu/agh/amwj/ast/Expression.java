package pl.edu.agh.amwj.ast;

import pl.edu.agh.amwj.value.types.Value;

/**
 * Created by Comarch on 2016-11-10.
 */
public interface Expression {
    Value evaluate();
}