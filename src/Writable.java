import java.io.IOException;

interface Writable {

        /**
         * Outputs the data of the implementing class to the specified file.
         *
         * @param filename the name of the file to output the data to
         * @throws IOException if an I/O error occurs while writing the data to the file
         */
        void output(String filename) throws IOException;
    }

