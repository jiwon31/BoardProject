import { useMutation } from "@tanstack/react-query";
import AuthApi from "api/auth-api";
import { SignUpRequest } from "types/auth";

export default function useAuth(authApi = new AuthApi()) {
  const signUp = useMutation<void, Error, SignUpRequest>((data) =>
    authApi.signUp(data)
  );

  return { signUp };
}
