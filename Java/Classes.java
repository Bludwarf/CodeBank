// Types g�n�riques

	/**
	 * Pour r�cup�rer la Class<T> d'une ArrayList<T> par exemple
	 */
	public static Class<?>[] getActualClassArguments(Field field)
	{
		final ParameterizedType pType = (ParameterizedType) field.getGenericType();
		final Type[] genTypes = pType.getActualTypeArguments();
		final Class<?>[] genClasses = new Class<?>[genTypes.length];
		for (int i = 0; i < genTypes.length; ++i)
		{
			final Type genType = genTypes[i];
			genClasses[i] = (Class<?>) genType;
//			if (genType instanceof ParameterizedType)
//			{
//				genClasses[i] = (Class<?>) ((ParameterizedType)genType).getRawType();
//			}
//			else
//			{
//				throw new NotImplementedException("Le type g�n�rique doit �tre un ParameterizedType");
//			}
		}
		return genClasses;
	}