package softuniBlog.entity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Entity
@Table(name="articles")
public class Article {
	
	private Integer id;
	
	private String title;
	
	private String content;
	
	private User author;
	
	public Article(String title, String content, User author) {
		this.title = title;
		this.content = content;
		this.author = author;
	}
	
	public Article() {
	
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(nullable = false)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(columnDefinition = "text", nullable = false)
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	@ManyToOne
	@JoinColumn(nullable = false,name = "authorId")
	public User getAuthor() {
		return author;
	}
	
	public void setAuthor(User author) {
		this.author = author;
	}
	
	@Transient
	public String getSummary(){
		int len = this.getContent().length();
		if (len>50){
			len = 50;
		}
		return this.getContent().substring(0,len)+"...";
	}
	
	@Transient
	public boolean isCurrentUserAuthor(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return this.author.getEmail().equals(userDetails.getUsername());
	}
	
}
