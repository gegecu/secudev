package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.IPChecker;

import com.mysql.fabric.xmlrpc.base.Array;

import model.Post;
import model.User;
import database.LogDAO;
import database.PostDAO;

/**
 * Servlet implementation class DownloadBackup
 */
@WebServlet("/download_backup")
public class DownloadBackup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadBackup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("user");
		
		if(loginUser != null && loginUser.isAdmin()) {
		
		List<File> files = Arrays.asList(dirListByDescendingDate(new File("C:\\ProgramData\\MySQL\\MySQL Server 5.6\\data\\tmp\\secudev")));
		
		int page = 1;
		int recordsPerPage = 10;
		if(request.getParameter("page") != null){
			try{
				page = Integer.parseInt(request.getParameter("page"));
			}catch(NumberFormatException e){
				
			}
		}

		if(page<=0)
			page = 1;
        
//        List<Post> posts = PostDAO.getPosts((page-1)*recordsPerPage,
//                                 recordsPerPage, null, "");
        int noOfRecords = files.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        
        if(noOfPages<page)
			page = noOfPages;
        
        if(recordsPerPage > files.size()) {
        	recordsPerPage = (files.size() % recordsPerPage) + (page-1)*recordsPerPage;
        	//System.out.println(recordsPerPage);
        }
        
        request.setAttribute("files", files.subList((page-1)*recordsPerPage, recordsPerPage));
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("backup.jsp").forward(request, response);
		}
		
		else {
			response.sendRedirect("login_landing");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Set the content type based to zip

		// List of files to be downloaded
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("user");
		
		if(loginUser != null && loginUser.isAdmin()) {
			String[] fileNames = request.getParameterValues("files");
			
			if(fileNames != null) {
				response.setContentType("Content-type: text/zip");
				response.setHeader("Content-Disposition", "attachment; filename=mytest.zip");
				
				List<File> files = new ArrayList();
				for(String fileName: fileNames) {
					files.add(new File("C:\\ProgramData\\MySQL\\MySQL Server 5.6\\data\\tmp\\secudev\\" + fileName));
				}
		
				ServletOutputStream out = response.getOutputStream();
				ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));
		
				for (File file : files) {
		
					System.out.println("Adding " + file.getName());
					zos.putNextEntry(new ZipEntry(file.getName()));
		
					// Get the file
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(file);
					} catch (FileNotFoundException fnfe) {
						// If the file does not exists, write an error entry instead of
						// file
						// contents
						zos.write(("ERROR could not find file " + file.getName()).getBytes());
						zos.closeEntry();
						System.out.println("Could find file " + file.getAbsolutePath());
						continue;
					}
		
					BufferedInputStream fif = new BufferedInputStream(fis);
		
					// Write the contents of the file
					int data = 0;
					while ((data = fif.read()) != -1) {
						zos.write(data);
					}
					fif.close();
		
					zos.closeEntry();
					System.out.println("Finished file " + file.getName());
				}
		
				zos.close();
			}
			response.sendRedirect("download_backup");
		}
		else {
			response.sendRedirect("login_landing");
		}
		
		
	}
	
	public File[] dirListByDescendingDate(File folder) {
		if (!folder.isDirectory()) {
			return null;
		}
		File files[] = folder.listFiles();
		Arrays.sort( files, new Comparator() {
			public int compare(final Object o1, final Object o2) {
				return new Long(((File)o2).lastModified()).compareTo
						(new Long(((File) o1).lastModified()));
			}
		}); 
		return files;
	} 

}
