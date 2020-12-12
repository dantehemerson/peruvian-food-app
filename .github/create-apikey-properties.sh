#!/bin/bash

set -e

filename="apikey.properties"

echo "Creating file $filename..."

required_envs=(
  PERUVIAN_FOOD_API
)

for variable in "${required_envs[@]}"
do 
  if ! [ -v $variable ]; then
    echo "❌ variable $variable missing"
    exit 1
  fi

  echo "$variable=\"${!variable}\"" >> $filename
done 

echo "🎉 $filename created correctly"