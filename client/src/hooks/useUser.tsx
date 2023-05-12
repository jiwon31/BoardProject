import { useMutation, useQueryClient } from "@tanstack/react-query";
import UserApi from "api/user-api";

export default function useUser(userApi = new UserApi()) {
  const queryClient = useQueryClient();

  const checkEmailDuplicate = useMutation<string, Error, string>((email) =>
    userApi.checkEmailDuplicate(email)
  );

  const checkUserNameDuplicate = useMutation<string, Error, string>(
    (userName) => userApi.checkUserNameDuplicate(userName)
  );

  return { checkEmailDuplicate, checkUserNameDuplicate };
}
