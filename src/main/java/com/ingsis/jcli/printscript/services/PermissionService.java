package com.ingsis.jcli.printscript.services;

import com.ingsis.jcli.printscript.clients.PermissionsClient;
import com.ingsis.jcli.printscript.common.PermissionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
  final PermissionsClient permissionsClient;

  @Autowired
  public PermissionService(PermissionsClient permissionsClient) {
    this.permissionsClient = permissionsClient;
  }

  public boolean hasPermission(PermissionType type, Long userId, Long snippetId) {
    ResponseEntity<Boolean> response =
        permissionsClient.hasPermission(type.name, snippetId, userId);

    if (response == null || response.getStatusCode().isError()) {
      // TODO
      return false;
    }

    return response.getBody() != null && response.getBody();
  }
}
