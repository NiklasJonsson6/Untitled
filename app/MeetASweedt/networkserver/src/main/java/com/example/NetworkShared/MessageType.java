package com.example.NetworkShared;

/**
 * omg java, get tagged unions already. (or just unions, that'd fucking work too)
 */

public enum MessageType
{
    GetMatches,
    AddMatch,
    GetAllPeople,
    ValidUserName,
    CreateUser,
    UpdateLocation,
    TerminateConnection,
    SendMessage,
    Error,
    VerifyPassword,
    GetMessages,
    GetPerson,
}
