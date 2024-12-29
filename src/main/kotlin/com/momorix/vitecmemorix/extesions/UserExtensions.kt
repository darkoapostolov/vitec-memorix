package com.momorix.vitecmemorix.extesions

import com.momorix.vitecmemorix.dto.UserDTO
import com.momorix.vitecmemorix.model.User


fun User.mapToDTO() = UserDTO(email, name)