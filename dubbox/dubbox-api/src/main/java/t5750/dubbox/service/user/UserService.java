package t5750.dubbox.service.user;

public interface UserService {
	User getUser(Long id);

	Long registerUser(User user);
}
