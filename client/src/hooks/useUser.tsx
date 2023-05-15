import { useMutation } from "@tanstack/react-query";
import UserApi from "api/user-api";
import { User, UpdateUserRequest } from "types/user";
import useRecoilUser from "./useRecoilUser";

export default function useUser(userApi = new UserApi()) {
  const { setUser } = useRecoilUser();

  const updateUserInfo = useMutation<User, Error, UpdateUserRequest>(
    (data) => userApi.updateUserInfo(data),
    {
      onSuccess: (data) => setUser(data),
    }
  );

  const checkEmailDuplicate = useMutation<string, Error, string>((email) =>
    userApi.checkEmailDuplicate(email)
  );

  const checkUserNameDuplicate = useMutation<string, Error, string>(
    (userName) => userApi.checkUserNameDuplicate(userName)
  );

  return { updateUserInfo, checkEmailDuplicate, checkUserNameDuplicate };
}
