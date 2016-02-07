# Java Yopmail Reader
#### What is Yopmail?
[YOPmail] *(Your Own Protection mail)* is a temporary e-mail service. They keep a message up for 8 days. [YOPmail wikia.com]
#### Who is going to use this tool?
This Tool was made for people who wants to automate certain task involving email receptions. 
I were looking for a tool similar like this but was not lucky. So i am sharing my yopmail reader for the comunity.
#### What does this tool?
  - List first incoming emails
  - List incoming emails from a specific page
  - Get the content of a specific email received *(from one element of above list)* 
  - Get the content of the last email received
  
#### How to use this tool?
This tool was designed following the singleton pattern. 
From [wikipedia][singletonwiki]: 
> The singleton pattern is a design pattern that restricts the instantiation of a class to one object. This is useful when exactly one object is needed to coordinate actions across the system. The concept is sometimes  generalized to systems that operate more efficiently when only one object exists, or that restrict the instantiation to a certain number of objects. The term comes from the mathematical concept of a singleton.

An example of utilization could be: 
```java
import java.io.IOException;
import java.util.List;

import com.AngelBarreraSanchez.Yopmail.Domain.Mail;

public class Example {
	
	public static void main(String[] args) {
		
		final String myYopMail = "abasanchez@yopmail.com";
		
		final YopmailReader yopmailReader = YopmailReader.getInstance();
		
		try {
			// Getting the content of the lastmail received
			final String lastMailContent = yopmailReader.getLastMailContent(myYopMail);
			System.out.println("Last email content: " );
			System.out.println(lastMailContent);
			// Getting the first 15 received mails
			List<Mail> listMail = yopmailReader.getIncomingMailList(myYopMail);
			for(final Mail mail : listMail){
				System.out.println("Mail from: " + mail.getSender());
				System.out.println("Mail subject: " + mail.getSubject());
				System.out.println("Mail content: ");
				System.out.println(yopmailReader.getMailContent(mail));
			}
			final int page = 2;
			// Getting the next 15 received mails (page 2)
			listMail = yopmailReader.getIncomingMailList(myYopMail, page);
			for(final Mail mail : listMail){
				System.out.println("Mail from: " + mail.getSender());
				System.out.println("Mail subject: " + mail.getSubject());
				System.out.println("Mail content: ");
				System.out.println(yopmailReader.getMailContent(mail));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
```
### Actual tool version
0.0.1
### BE CAREFUL!!!
During the testing phase, they blocked me from accessing to the YopMail web, now all time i am trying to enter its site i have got a 403 error. You know, a stress test can make yopmail to understand you are trying to attack its site. So, use this tool with head before getting your ip banned.

Another things to know is the probability to request mail content and get an IOException. These could be because sometimes, randomly, yopmail requests you to resolve a captcha. 

### Next Steps
 - Get the date and time of a received mail
 - Send mails to another yopmails accounts
 - We can analyze the way to avoid captchas requests. 
 - Improve the times of dom navigation.
 - Push to maven repo?

### Development
Want to contribute? Great!
I will wait your pull requests :)

   [YOPmail wikia.com]: <http://spam.wikia.com/wiki/YOPmail>
   [YOPmail]: <http://yopmail.com/>
   [singletonwiki]: <https://en.wikipedia.org/wiki/Singleton_pattern>

