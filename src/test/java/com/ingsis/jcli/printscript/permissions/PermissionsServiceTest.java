package com.ingsis.jcli.printscript.permissions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ingsis.jcli.printscript.clients.PermissionsClient;
import com.ingsis.jcli.printscript.clients.SnippetsClient;
import com.ingsis.jcli.printscript.common.PermissionType;
import com.ingsis.jcli.printscript.services.PermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class PermissionsServiceTest {
  @Mock private SnippetsClient snippetsClient;

  @Mock private PermissionsClient permissionsClient;

  @InjectMocks private PermissionService permissionsService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testPermissionFormat() {
    when(permissionsClient.hasPermission(PermissionType.FORMAT.name, 1L, 2L))
        .thenReturn(ResponseEntity.ok(true));
    when(permissionsClient.hasPermission(PermissionType.FORMAT.name, 1L, 1L))
        .thenReturn(ResponseEntity.ok(false));
    boolean permission = permissionsService.hasPermission(PermissionType.FORMAT, 2L, 1L);
    assertTrue(permission);
    boolean permission2 = permissionsService.hasPermission(PermissionType.FORMAT, 1L, 1L);
    assertFalse(permission2);
    verify(permissionsClient).hasPermission(PermissionType.FORMAT.name, 1L, 2L);
    verify(permissionsClient).hasPermission(PermissionType.FORMAT.name, 1L, 1L);
  }
}
