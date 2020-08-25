package com.mthree.bsm.repository;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Implements {@link AuditDao}, writing audit messages to a text file called {@code auditlog.txt}.
 */
@Component
public class TextFileAuditDao implements AuditDao {

    private final String AUDIT_FILE = "auditlog.txt";

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
     */
    @Override
    public void writeMessage(String message) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(AUDIT_FILE));

        writer.println("[" + LocalDateTime.now() + "] " + message);
    }

}
