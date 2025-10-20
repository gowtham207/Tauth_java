import * as Yup from "yup";

export const LoginValidationSchema = Yup.object({
    email: Yup.string()
      .email("Invalid email format")
      .required("Email is required"),
    password: Yup.string().min(6, "At least 6 characters").required("Password is required"),
  });