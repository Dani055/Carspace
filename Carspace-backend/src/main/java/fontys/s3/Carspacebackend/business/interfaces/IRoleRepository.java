package fontys.s3.Carspacebackend.business.interfaces;

import fontys.s3.Carspacebackend.domain.IRole;

public interface IRoleRepository {
    IRole findById(Long id);
}
