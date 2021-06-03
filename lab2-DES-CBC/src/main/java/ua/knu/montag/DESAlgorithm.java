package ua.knu.montag;
import sun.security.util.BitArray;

import static ua.knu.montag.CipherTables.*;

public class DESAlgorithm {

    //split block 48 bit to 8 block 6bit
    private BitArray[] splitBlock(BitArray E) {
        BitArray[] blocks = new BitArray[8];

        for (int i = 0; i < 8; i++) {
            blocks[i] = new BitArray(6);
            for (int j = 0; j < 6; j++) {
                blocks[i].set(j, E.get(i * 6 + j));
            }
        }
        return blocks;
    }

    //extend input block from 32 bit to 48
    private BitArray extendBlock(BitArray R) {
        BitArray E = new BitArray(48);
        for (int i = 0; i < 48; i++) {
            E.set(i, R.get(extensionTable[i]));
        }
        return E;
    }

    private BitArray xor(BitArray a, BitArray b) {
        if (a.length() != b.length()) throw new IllegalArgumentException("input has no same size");

        BitArray result = new BitArray(b.length());
        for (int i = 0; i < a.length(); i++) {
            result.set(i, a.get(i) ^ b.get(i));
        }
        return result;
    }


    private BitArray feistelHelper(BitArray R, BitArray key) {
        BitArray result = new BitArray(32);

        BitArray extendBlock = extendBlock(R);
        extendBlock = xor(extendBlock, key);


        BitArray[] blocks = splitBlock(extendBlock);
        BitArray tempRes = new BitArray(32);
        for (int i = 0; i < 8; i++) {
            int a = toInt(blocks[i].get(0)) * 2 + toInt(blocks[i].get(5));
            int b = toInt(blocks[i].get(1)) * 8 + toInt(blocks[i].get(2)) * 4 + toInt(blocks[i].get(3)) * 2 + toInt(blocks[i].get(4));
            int c = blockTransformationTables[i][a][b];
            for (int j = 3; j >= 0; j--) {
                tempRes.set(i * 4 + j, (c % 2 == 1));
                c = c / 2;
            }

        }
        for (int i = 0; i < 32; i++) {
            result.set(i, tempRes.get(replaceTableP[i]));
        }
        return result;
    }


    private BitArray feistelEncrypt(BitArray input, CipherKey key) {
        BitArray output = new BitArray(64),
                L = new BitArray(32),
                R = new BitArray(32),
                temp, f;
        for (int i = 0; i < 32; i++) {
            L.set(i, input.get(i));
            R.set(i, input.get(i + 32));
        }

        for (int i = 0; i < 16; i++) {
            BitArray subKey = key.getKey(i);
            temp = R;
            f = feistelHelper(R, subKey);
            R = xor(L, f);
            L = temp;
        }

        for (int i = 0; i < 32; i++) {
            output.set(i, L.get(i));
            output.set(i + 32, R.get(i));
        }

        return output;
    }

    private BitArray feistelDecrypt(BitArray b, CipherKey key) {
        BitArray output = new BitArray(64),
                L = new BitArray(32),
                R = new BitArray(32),
                temp, f;
        for (int i = 0; i < 32; i++) {
            L.set(i, b.get(i));
            R.set(i, b.get(i + 32));
        }

        for (int i = 0; i < 16; i++) {
            BitArray subKey = key.getKey(15 - i);
            temp = L;
            f = feistelHelper(L, subKey);
            L = xor(R, f);
            R = temp;
        }

        for (int i = 0; i < 32; i++) {
            output.set(i, L.get(i));
            output.set(i + 32, R.get(i));
        }
        return output;
    }

    private BitArray replaceS(BitArray input) {
        BitArray output=new BitArray(input.length());
        for (int i = 0; i < 64; i++) {
            output.set(i, input.get(replaceTable1[i]));
        }
        return output;
    }

    private BitArray replaceB(BitArray input) {
        BitArray output=new BitArray(input.length());
        for (int i = 0; i < 64; i++) {
            output.set(replaceTable1[i], input.get(i));
        }
        return output;
    }

    public String encrypt(String input, String keyS, String initStr) {
        CipherKey key = new CipherKey(keyS);

        while (input.length() % 8 != 0)
            input = input.concat(String.valueOf('\0'));
        String output = "";

        BitArray previousEncryptedBlock = new BitArray(64, initStr.getBytes());


        for (int i = 0; i < input.length() / 8; i++) {
            BitArray arr = new BitArray(64, input.substring(i * 8, (i + 1) * 8).getBytes());

            arr = xor(previousEncryptedBlock, arr);

            arr=replaceS(arr);
            arr = feistelEncrypt(arr, key);
            arr=replaceB(arr);

            previousEncryptedBlock = arr;
            output = output.concat(new String(arr.toByteArray()));
        }

        return output;
    }

    public String decrypt(String input, String keyS, String initStr) {
        if (input.length() % 8 != 0)
            throw new IllegalArgumentException("input has incorrect size");

        CipherKey key = new CipherKey(keyS);
        String result = "";

        BitArray previousEncryptedBlock = new BitArray(64, initStr.getBytes());

        for (int i = 0; i < input.length() / 8; i++) {
            if (i != 0)
                previousEncryptedBlock = new BitArray(64, input.substring((i - 1) * 8, (i) * 8).getBytes());

            BitArray arr = new BitArray(64, input.substring(i * 8, (i + 1) * 8).getBytes());

            arr=replaceS(arr);
            arr=feistelDecrypt(arr, key);
            arr=replaceB(arr);

            arr = xor(previousEncryptedBlock, arr);
            result = result.concat(new String(arr.toByteArray()));
        }

        return result;
    }
    public static int toInt(Boolean b) {
        return b ? 1 : 0;
    }
}
