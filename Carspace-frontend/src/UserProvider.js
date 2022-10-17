import React, { createContext, useCallback, useState } from 'react';

export const UserContext = createContext();

export function UserProvider({ children }) {
    const [loggedUser, setLoggedUser] = useState(null);

    return (
        <UserContext.Provider value={{ loggedUser, setLoggedUser }}>
            {children}
        </UserContext.Provider>
    )
}
