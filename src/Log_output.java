// Give classes that need to send output a short form method for it
// Isolating output this way would make it easy to point this to a file or another output sink

/* NOTE BELOW
/* original code from Professor's Project 2 Source Code
/* modifications were made to add functionality overall for Project 3 requirements
/* and supplies were implemented
 */
public interface Log_output {
    default void out(String msg) {
        System.out.println(msg);
    }
}