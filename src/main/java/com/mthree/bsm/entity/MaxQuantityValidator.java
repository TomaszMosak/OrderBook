package com.mthree.bsm.entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxQuantityValidator implements ConstraintValidator<MaxQuantity, Trade> {

    /**
     * Initializes the validator in preparation for {@link #isValid(Trade, ConstraintValidatorContext)} calls. The
     * constraint annotation for a given constraint declaration is passed.
     * <p>
     * This method is guaranteed to be called before any use of this instance for validation.
     * <p>
     * The default implementation is a no-op.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(MaxQuantity constraintAnnotation) {

    }

    /**
     * Implements the validation logic. The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured by the implementation.
     *
     * @param trade   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Trade trade, ConstraintValidatorContext context) {
        return trade.getQuantity() <= trade.getBuyOrder().getSize() &&
               trade.getQuantity() <= trade.getSellOrder().getSize();
    }

}
