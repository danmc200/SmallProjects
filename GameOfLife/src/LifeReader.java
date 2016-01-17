import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class LifeReader
{
    public BufferedReader imageReader;

	public LifeReader(String fileLocation)
	{
		try {
			imageReader = new BufferedReader(new FileReader(fileLocation));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public char [][] readNextImage(int maxR)
	{
		char [][] image = new char [maxR][];
		try {
			for(int i = 0; i < maxR; image[i++] = imageReader.readLine().toCharArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public boolean skipLine()
	{
		try {
			return imageReader.readLine() != null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
