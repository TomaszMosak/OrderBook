package com.mthree.bsm.repository;

import java.io.IOException;

/**
 * A repository for writing audit messages to a log in the system. Provides a single method {@link
 * #writeMessage(String)} to perform this.
 */
public interface AuditDao {

    /**
     * Writes a message to the audit log. Log entries created by this method will be of the form
     *
     * <pre>{@code [$TIMESTAMP] $MESSAGE}</pre>
     * <p>
     * Messages should be as simple as possible and show clearly what has happened. They must begin with a verb in the
     * infinitive form (e.g. "add", "edit", "delete"). They should only represent a single, atomic action. Multiple
     * actions made by some code should split up audit messages. For example, rather than
     * <pre>{@code repository.add(myThing);
     * repository.delete(yourThing);
     * audit.writeMessage("Add " + myThing.getName() + " to repository", delete" + yourThing.getName() + " from repository.");}</pre>
     * , prefer
     * <pre>{@code repository.add(myThing);
     * audit.writeMessage("Add " + myThing.getName() + " to repository.");
     * repository.delete(yourThing);
     * audit.writeMessage("Delete" + yourThing.getName() + " from repository."); }</pre>
     *
     * @throws IOException when the audit log cannot be written to.
     */
    void writeMessage(String message) throws IOException;

}
