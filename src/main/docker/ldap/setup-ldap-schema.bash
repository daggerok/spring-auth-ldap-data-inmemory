#!/bin/bash

set -exu

while ! netcat -z localhost ${LDAP_PORT}; do
  sleep 1
done

# Create the schema
ldapadd -c -H ldap://localhost:${LDAP_PORT} -x -D "cn=admin,dc=daggerok,dc=com" -w password -f /setup-ldap-schema.ldif

# Set user passwords

ldappasswd -H ldap://localhost:${LDAP_PORT} -x -D "cn=admin,dc=daggerok,dc=com" -w password -s password "uid=user1,ou=users,dc=daggerok,dc=com"
ldappasswd -H ldap://localhost:${LDAP_PORT} -x -D "cn=admin,dc=daggerok,dc=com" -w password -s password "uid=user2,ou=users,dc=daggerok,dc=com"
ldappasswd -H ldap://localhost:${LDAP_PORT} -x -D "cn=admin,dc=daggerok,dc=com" -w password -s password "uid=superuser,ou=admins,dc=daggerok,dc=com"
