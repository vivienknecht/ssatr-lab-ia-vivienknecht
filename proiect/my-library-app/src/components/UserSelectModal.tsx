import React, { useEffect, useState } from "react";
import { getAllUsers } from "../api/api";
import type { User } from "../types/User";

interface Props {
  open: boolean;
  onClose: () => void;
  onSelect: (userId: number) => void;
}

export const UserSelectModal: React.FC<Props> = ({
  open,
  onClose,
  onSelect,
}) => {
  const [users, setUsers] = useState<User[]>([]);

  useEffect(() => {
    if (open) {
      getAllUsers().then((res) => setUsers(res.data));
    }
  }, [open]);

  if (!open) return null;

  return (
    <div style={overlayStyle}>
      <div style={modalStyle}>
        <h3>Select User</h3>

        {users.map((user) => (
          <div
            key={user.id}
            style={userItemStyle}
            onClick={() => {
              onSelect(user.id);
              onClose();
            }}
          >
            {user.name} ({user.role})
          </div>
        ))}

        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

const overlayStyle: React.CSSProperties = {
  position: "fixed",
  top: 0,
  left: 0,
  right: 0,
  bottom: 0,
  background: "rgba(0,0,0,0.5)",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
};

const modalStyle: React.CSSProperties = {
  background: "white",
  padding: 20,
  borderRadius: 8,
  width: 300,
};

const userItemStyle: React.CSSProperties = {
  padding: 8,
  cursor: "pointer",
  borderBottom: "1px solid #ddd",
};