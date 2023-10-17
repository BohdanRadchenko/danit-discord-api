package com.danit.discord.services;

import com.danit.discord.dto.user.UserInviteRequest;
import com.danit.discord.entities.Invite;
import com.danit.discord.entities.User;
import com.danit.discord.enums.InviteType;
import com.danit.discord.enums.StatusType;
import com.danit.discord.repository.InviteRepository;
import com.danit.discord.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private final UserService userService;
    public Invite created(String userEmail, UserInviteRequest request, InviteType type) {
        User userFrom = userService.getByEmail(userEmail);
        User userTo = userService.getById(request.getId());
        return inviteRepository.save(new Invite(userFrom.getId(), userTo.getId(), type, StatusType.PENDING));
    }
    public StatusType inviteAccepted(StatusType statusType, Long inviteId) {
        Invite invite = inviteRepository.getById(inviteId);
        invite.setStatus(StatusType.ACCEPTED);
        inviteRepository.save(invite);
        userService.addFriend(userService.getById(invite.getFromId()), userService.getById(invite.getToId()));
        return statusType.ACCEPTED;
    }
    public StatusType inviteRejected(StatusType statusType, Long inviteId) {
        Invite invite = inviteRepository.getById(inviteId);
        invite.setStatus(StatusType.REJECT);
        inviteRepository.save(invite);
        return statusType.REJECT;
    }
    public List<Invite> getAllInviteToId(Long id) {
        return inviteRepository.findAllByToId(id);
    }
}
