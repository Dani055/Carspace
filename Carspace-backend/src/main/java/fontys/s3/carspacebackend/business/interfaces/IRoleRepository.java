package fontys.s3.carspacebackend.business.interfaces;

import fontys.s3.carspacebackend.domain.IRole;

public interface IRoleRepository {
    IRole findById(Long id);
}
