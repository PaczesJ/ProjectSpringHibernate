package pl.coderslab.project.Converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.coderslab.project.dao.UserDao;
import pl.coderslab.project.entities.User;



public class UserConverter implements Converter<String, User> {

    @Autowired
    private UserDao userDao;


    @Override
    public User convert(String source) {
        return userDao.read(Integer.parseInt(source));
    }
}
