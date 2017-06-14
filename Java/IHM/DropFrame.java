package com.capgemini.exs.qc.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.TransferHandler;

public class DropFrame extends JFrame
{
	public DropFrame()
	{
		setTransferHandler(handler);
	}
	
	private TransferHandler handler = new TransferHandler()
    {

		// Est-ce qu'on autorise le drop ?
		@Override
		public boolean canImport(TransferHandler.TransferSupport support)
		{
            if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
            {
                return false;
            }

            return true;
        }

		// drop
		@Override
        public boolean importData(TransferHandler.TransferSupport support)
		{
            if (!canImport(support)) return false;
            
            Transferable t = support.getTransferable();

            try
            {
                java.util.List<File> files = (java.util.List<File>)t.getTransferData(DataFlavor.javaFileListFlavor);

                for (File f : files)
                {
                	// ...
                }
            }
            catch (UnsupportedFlavorException e)
            {
                return false;
            }
            catch (IOException e)
            {
                return false;
            }

            return true;
        }
    };
}
