package dn.duplicatefileremover;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileInputStream;


/**
 * @author David Newton
 *
 * Scans the user's default downloads directory for duplicate files and removes them.
 */
public class DuplicateFileRemover {
	
	public static void main(String[] args)
	{	
		Path userPath = Paths.get(System.getProperty("user.home") , "Downloads");	
		File dir = new File(userPath.toString());
		File[] fileList = dir.listFiles();
		
		for(int i = 0; i < fileList.length; i++)
		{			
			//System.out.println(fileList[i]);
			for(int j = i+1; j < fileList.length; j++)
			{
				if (fileList[i].length() == fileList[j].length())
				{
					if(CompareFiles(fileList[i], fileList[j]))
					{
						
						try{
							System.out.println("> Duplicate Found: " + fileList[j].getName());
							System.out.println("> Deleting...");
							fileList[j].delete();
						}catch (Exception e){
							e.printStackTrace();
						}
					}				
				}
			}
		}
		
		System.out.println("> Finished.");
	}
	private static boolean CompareFiles(File file, File file2) {
		
		try
		{
			FileInputStream fileStream = new FileInputStream(file);
			FileInputStream fileStream2 = new FileInputStream(file2);
				
			boolean result = true;
			while(result == true)
			{
				int fileb = fileStream.read();
				@SuppressWarnings("unused")
				int fileb2 = fileStream2.read();
				if(fileStream.read() != fileStream2.read())
				{
					result = false;
					break;
				}
				if (fileb == -1)//EOF
					break;
			}
			
			fileStream.close();
			fileStream2.close();
			return result;
			
		}catch (FileNotFoundException e){
			return false;
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
}