

    /**
     * Copie le fichier source vers le fichier dest et l'écrase s'il existe déjà
     * @param source fchier source
     * @param dest fichier destination
     * @throws IOException en cas d'erreur d'E/S
     * 
     * @author http://java.sun.com/docs/books/performance/1st_edition/html/JPIOPerformance.fm.html
     */
    public static void copier(final File source, final File dest)
    throws IOException
    {
        
        if (!source.exists())
        {
            throw new IOException("Impossible de copier le fichier \"" + source.getPath() + "\" car il n'existe pas");
        }
        
        if (dest.exists())
        {
            loggerActivite.info("Le fichier \"" + dest.getPath() + "\" va être écrasé par le fichier \""
                + source.getPath() + "\"");
        }

        // Lecture par segments de 0.5Mo
        final byte[] buffer = new byte[512 * 1024];

        InputStream in = null;
        OutputStream out = null; 
        try
        {
            in = new FileInputStream(source);
            out = new FileOutputStream(dest);
            while (true)
            {
                synchronized (buffer)
                {
                    final int amountRead = in.read(buffer);
                    if (amountRead == -1)
                    {
                        break;
                    }
                    out.write(buffer, 0, amountRead); 
                }
            } 
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
            if (in != null)
            {
                in.close();
            }
        }
    }