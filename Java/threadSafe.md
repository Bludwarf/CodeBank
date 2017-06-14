# Thread Safe pour les SimpleDateFormat

    private static final ThreadLocal<SimpleDateFormat> formatter = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat("dd/MM/yyyy HH'h'mm");
        }
    };