package ru.spaceinvasion.services.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Profile("postgres")
public class UserServicePostgres implements UserService {
}
